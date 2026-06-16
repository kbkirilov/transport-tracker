Build the image:
docker build -t transport-tracker .

Run the image:
docker run -d --name transport-tracker -p 8080:8080 transport-tracker

**Manually pushing image to GitHub Container Registry:**
1. `docker login ghcr.io` - Use Github username and for password the personal access token generated
2. build the docker image
3. Re-tag the image in the required format - `docker tag <image-name>:<tag> ghcr.io/<your-github-username>/<image-name>:<latest>`
4. Done