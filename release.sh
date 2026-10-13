#!/usr/bin/env bash

set -eu -o pipefail

VERSION_PREV=$(egrep -o '[0-9]+' <app/version.gradle.kts)

VERSION_NEXT=$(( VERSION_PREV + 1 ))

# check if we are clear to go
if [[ -n "$(git status --porcelain)" ]] ; then
  echo "Please commit all your changes and clean working directory."
  git status
  exit 1
fi

echo "Release steps:"
echo " * Increase version to $VERSION_NEXT"
echo " * Start release of version $VERSION_NEXT"
echo " * Upload apk and update.json to the GitHub release"
echo " * Create tag for version v$VERSION_NEXT"
echo ""

if ${VERBOSE:-true} ; then
  # user needs to type yes to continue
  read -p 'Is this correct?: ' CONFIRM || exit 1
  [[ "$CONFIRM" == "yes" ]] || exit 1
fi

GITHUB_REPO=NanoPolymer/pr0gramm-app

# user-facing changes since the last release: feat and fix commits, without the ci scope
CHANGELOG=$(git log --format='%s' "$(git describe --tags --abbrev=0)..HEAD" \
  | grep -E '^(feat|fix)(\([^)]*\))?: ' | grep -vE '^[a-z]+\(ci\)' \
  | sed -E 's/^(feat|fix)(\([^)]*\))?: /- /' || true)

function format_version() {
  local VERSION=$1
  echo -n "1."$(( VERSION/10 ))'.'$((VERSION % 10 ))
}

function deploy_upload_apk() {
  local APK_ALIGNED=app/build/outputs/apk/release/app-release.apk
  local TAG=$(format_version ${VERSION_NEXT})
  local UPDATE_JSON=app/build/outputs/apk/release/update.json

  # the app reads this file from releases/latest/download/update.json
  jq -n --argjson version "$VERSION_NEXT" \
    --arg apk "https://github.com/$GITHUB_REPO/releases/download/$TAG/app-release.apk" \
    --arg changelog "$CHANGELOG" \
    '{version: $version, apk: $apk, changelog: $changelog}' > "$UPDATE_JSON"

  echo "Create the GitHub release with the apk and update.json"
  gh release create "$TAG" "$APK_ALIGNED" "$UPDATE_JSON" \
    --repo "$GITHUB_REPO" --title "v$TAG" --notes "${CHANGELOG:-No user-facing changes.}"
}

# increase app version for further development
echo "extra[\"appVersion\"] = $VERSION_NEXT" > app/version.gradle.kts

trap 'git checkout app/version.gradle.kts' ERR

# compile code and create apks
rm -rf -- model/build/* app/build/*
./gradlew --console=plain --no-daemon clean assembleRelease

# verify apk
if ! unzip -t app/build/outputs/apk/release/app-release.apk | grep publicsuffixes.gz ; then
    echo "Could not find publicsuffixes.gz in the apk"
    exit 1
fi

git add app/version.gradle.kts
git commit -m "Released version $VERSION_NEXT"

trap - ERR

# create tag for this version
git tag -a "$(format_version ${VERSION_NEXT})" \
        -m "Released version $(format_version ${VERSION_NEXT})"

git push
git push --tags

deploy_upload_apk

# generate debug sources in a final step.
echo "Prepare next dev cycle..."
./gradlew --console=plain --no-daemon generateDebugSources > /dev/null
