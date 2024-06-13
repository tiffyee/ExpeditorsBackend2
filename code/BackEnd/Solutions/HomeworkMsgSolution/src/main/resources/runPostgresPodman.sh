#Detached
#podman run -dt --name my-postgres -e POSTGRES_PASSWORD=1234 -v "/otherhome/podman/postgres-podman/data:/var/lib/postgresql/data" -p 5432:5432 postgres

#With non persistent container (no volume mapped)
podman run -dt --name my-postgres -e POSTGRES_PASSWORD=password -e PAGER=/usr/bin/less -e LESS='-X -R -i' -p 5432:5432 docker.io/library/postgres

#Interactive
#podman run -it --name my-postgres -e POSTGRES_PASSWORD=1234 -v "/otherhome/podman/postgres-podman/data:/var/lib/postgresql/data:Z" -p 5432:5432 postgres


#To set up users:
#cat 0-postgres-setup-schema.sql | podman exec -i my-postgres psql -Upostgres