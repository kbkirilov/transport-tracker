Build the image:
docker build -t transport-tracker .

Run the image:
docker run -d --name transport-tracker -p 8080:8080 transport-tracker