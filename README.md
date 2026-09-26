# DevOps 26

[![Manuel's pipeline](https://github.com/kllmanu/devops/actions/workflows/manuel.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/manuel.yml)
[![Nathan's pipeline](https://github.com/kllmanu/devops/actions/workflows/nathan.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/nathan.yml)
[![Ruben's pipeline](https://github.com/kllmanu/devops/actions/workflows/ruben.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/ruben.yml)

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
- [x] create a docker image
- [x] stop old container
- [x] remove old container
- [x] start container

**Problem #1:** The docker engine is running as root, which means every file it creates will be of user and group `root`. To workaround this, we map the user and group from the host to the container with the `docker run -u "$(id -u):$(id -g)"`.

**Problem #2:** Once a pipeline is finished, it deletes (post job cleanup) the `build/` folder created by gradle. We **usually try to verify every step** to get a better understanding of the things we do in our pipeline. The `build/` folder is created succesfully, but it is just cleaned afterwards. This is usually not a problem, because we create the docker image with the jar file right after. But it was definitely a lesson we learned the hard way!
