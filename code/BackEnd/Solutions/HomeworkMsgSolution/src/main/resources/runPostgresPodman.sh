#Detached
#podman run -dt --name my-postgres -e POSTGRES_PASSWORD=1234 -v "/otherhome/podman/postgres-podman/data:/var/lib/postgresql/data" -p 5432:5432 postgres

#podman run -dt --name my-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 postgres
podman run -dt --name my-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 docker.io/library/postgres

#Interactive
#podman run -it --name my-postgres -e POSTGRES_PASSWORD=1234 -v "/otherhome/podman/postgres-podman/data:/var/lib/postgresql/data:Z" -p 5432:5432 postgres
