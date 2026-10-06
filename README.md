# DevOps 26

[![Manuel's pipeline](https://github.com/kllmanu/devops/actions/workflows/manuel.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/manuel.yml)
[![Nathan's pipeline](https://github.com/kllmanu/devops/actions/workflows/nathan.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/nathan.yml)
[![Ruben's pipeline](https://github.com/kllmanu/devops/actions/workflows/ruben.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/ruben.yml)

Please note: We're currently working on Nathan's pipeline together, so his pipeline includes all the lectures as of now.

This is our repository for the [DevOps course at FHV](https://www.fhv.at/en/course/cc/079273000113/024717050608), University of Applied Sciences Vorarlberg. DevOps is the integration and automation of **software development and operations**. The aim of the course is to convey the interlinking between both.

We are using multiple pipelines, so everyone is able to play around with, thus we have multiple applications on the server running:

- http://10.0.40.170:8081 (Manuel's pipeline)
- http://10.0.40.170:8082 (Nathan's pipeline)
- http://10.0.40.170:8083 (Ruben's pipeline)

## Documentation

Here we keep our notes from the lectures, the things we got to know and the things we messed up. :)

### Lecture 1 — Setup

Our first lecture was all about the initial setup and getting familiar with bash, git and the command line in general. We generated SSH key pairs for each member and sent the public keys to our teacher. The project is just a [Spring Boot Application](https://start.spring.io/) with [gradle](https://gradle.org/) as build system using Java 21. We also created a simple `@Controller` and added some unit tests.

#### Setup SSH

- Generate an SSH key with: `ssh-keygen -t rsa -b 4096` and
- create an `~/.ssh/config` file for [aliases](https://askubuntu.com/questions/942279/create-alias-for-ssh-connecting)
- use `ssh devops` to login to our server and
- `exit` to disconnect

### Lecture 2 — Actions and Runners

First of all, we installed a [self-hosted runner](https://docs.github.com/en/actions/concepts/runners/self-hosted-runners) on our server. This is required, because the server at the FHV can't be reached from the outside due to security concerns. The self-hosted runner then polls the repository for changes and runs the pipelines on every commit (on the main branch as of now).

Next, everyone (!!) created a build pipeline to play and get a feeling. Our first pipeline was just a "Hello, world". Nothing fancy. It is important to run all the build tools inside docker, we don't want to install anything on the server. The pipeline can just be created on GitHub. It is the easier way, because it has builtin documentation, syntax highlighting, auto completion and even a marketplace for common workflows. However, it is also possible to create our own workflows in a yaml file in the `.github/workflows/` on our local machine.

<img width="1920" height="923" alt="image" src="https://github.com/user-attachments/assets/ec08cdff-078b-45f7-8c17-b197ad602ef2" />

The [GitHub Actions](https://github.com/kllmanu/devops/actions) shows all the pipelines. It allows us to investigate issues if we need to.

### Lecture 3 ­— Building and Testing

This lecture was all about getting to know plain `docker` commands, no `docker compose` so far. We want to get there, step by step, not all at once.

- [x] checkout repository
- [x] build with gradle image
- [x] run unit tests (part of the build step)
- [x] build a docker image
- [x] stop old container
- [x] remove old container
- [x] start new container

We built the application with the [official gradle image ](https://hub.docker.com/_/gradle) and run it with the [eclipse temurin](https://hub.docker.com/_/eclipse-temurin) image.

<img width="2560" height="1440" alt="image" src="https://github.com/user-attachments/assets/b4bf1519-d959-42f9-95bb-9872db87cb64" />

### Challenges we encountered

- The docker engine is running as root, which means every file it creates will be of user and group `root`. To workaround this, we use `docker run -u "$(id -u):$(id -g)"` to map the user and group from the host to the container with.
- Once a pipeline is finished, it deletes (post job cleanup) the `build/` folder created by gradle. We **usually try to verify every step** to get a better understanding of the things we do in our pipeline. The `build/` folder is created succesfully, but it is just cleaned afterwards. This is usually not a problem, because we create the docker image with the jar file right after. But it was definitely a lesson we learned the hard way!

### Lecture 4 — Testing, Tagging, Pushing

- To wait for the container to be ready, we simply `sleep` 30 seconds.
- The integration test just `curl`'s the response and `grep`'s for "running".
- We name our images with `latest` for the most recent one but also tag them with the commit SHA.

<img width="2560" height="1440" alt="Screenshot From 2026-10-06 23-22-02" src="https://github.com/user-attachments/assets/0bd8a63e-60b7-42a7-84e2-b88e6b324d51" />

In order to verify our repository and pushed images we have installed [crane](https://github.com/google/go-containerregistry/blob/main/cmd/crane/doc/crane.md).
