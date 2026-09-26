# DevOps 26

[![Manuel's pipeline](https://github.com/kllmanu/devops/actions/workflows/manuel.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/manuel.yml)
[![Nathan's pipeline](https://github.com/kllmanu/devops/actions/workflows/nathan.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/nathan.yml)
[![Ruben's pipeline](https://github.com/kllmanu/devops/actions/workflows/ruben.yml/badge.svg)](https://github.com/kllmanu/devops/actions/workflows/ruben.yml)

This is our repository for the [DevOps course at FHV](https://www.fhv.at/en/course/cc/079273000113/024717050608), University of Applied Sciences Vorarlberg. DevOps is the integration and automation of **software development and operations**. The aim of the course is to convey the interlinking between both.

## Documentation

Here we keep our notes from the lectures, the things we got to know and the things we messed up. :)

### Lecture 1

Our first lecture was all about the initial setup and getting familiar with bash, git and the command line in general. We generated SSH key pairs for each member and sent the public keys to our teacher. In order to make things simple, we installed [fzf](https://github.com/junegunn/fzf) and got to know about [how to get started with tmux](https://github.com/tmux/tmux/wiki/Getting-Started) once we got our server. 

The project is just a [Spring Boot Application](https://start.spring.io/) with [gradle](https://gradle.org/) as build system. We also created a simple controller and added some unit tests.

#### Setup SSH 

- Generate an SSH key with: `ssh-keygen -t rsa -b 4096` and
- create a `~/.ssh/config` file for convenience:

```
Host devops
HostName 127.0.0.1
User bob
```

- use `ssh devops` to login to our server and
- `exit` to disconnect

