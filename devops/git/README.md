# Git Best Practices Guide
At this point we're going to talk about best practices to work with git.

## Index

* [Prologue](#prologue)
* [Getting Started](#getting-started)
	* [Setting up git](setting-up-git)
	* [gitignore](#gitignore)
	* [Use the shell](#use-the-shell)
* [Git Flow](#git-flow)
	* [Main branches](#main-branches)
	* [Support branches](#support-branches)
* [Advices](#advices)
	* [Versioning](#versioning)
 	* [README.md](#readme-md)
	* [Redmine's id-track](#redmines-id-track)
	* [Use hooks](#use-hooks)
	* [Use tools](#use-tools)
* [References](#references)


## Prologue
This guide try to help to working teams to use git and git flows correctly. Is very recommended that all team's members working with git in the same way, for this goal this guide will contain some usefull advices and tips for the correct use of git and help to unify the way of work with it.

## Getting Started

### Setting up git
 The first thing to do is setting up git.

### gitignore
When you work with git there are some files that's you shouldn't upload to the repository like configuration files, ide files, files with passwords or connection params, etc. For this goal, exists the .gitignore file, and you need create it and include here the list of files or folders to exclude of the version control.

It's very important **include at .gitignore the files that contains confidential information like credendials** to services, by security reasons these type of information should never be uploaded to the remote repository.

Usually, you can create some templates files for this type of information with its fields empty. For example a file db.config.sample with content:
```
db.host=
db.url=
db.password=
```
Next, there are some folders and files thats usually have to be added at .gitignore list depending on the programming languaje or ide used:

* General files or folders:
	* .log
	* log/
	* tmp/
	* .settings/
* Java applications:
	* target/
	* .project
	* .classpath
	* target/
	* bin/
	* .metadata/
	* RemoteSystemsTempFiles/
	* Servers/
* NodeJS:
	* node_modules/
* Python:
	* Files with pyc extension: .pyc
* The jetbrains ides like Pycharm or WebStorm, have a settings folder  with the name:
	* idea/

### Use the shell

There are a lot of IDEs that have its own git plugins, but very often this plugins doesn't works fine in determinated features. The most reliable way to syncronize with git is install git and use the shell to work with git.

## Git Flow
 ![alt text](static/gitflow.png "GIT FLOW GRAPH")

### Main branches

### Support branches

## Advices

### Versioning

### Redmine's id-track

### Hooks

### README.md
Write a text file called README.md in the main directory of project. This file must have the main information about the code, how use and configure it and how can execute it. This file must contains:

* Information about the application configuration, must cover configuration files, dependencies, libraries or tools that are needed for use or install it.
* Information about how install and execute the application.
* Information to test the application and test the qa quality.
* If is a application or library that you can invoke, like an web application or an service web, is important include the information about you can call it.
* For applications which will run on Docker container, is important include here the information necessary to build the Docker image and how run the container.

### References

* [GitHub Help](https://help.github.com/)
* [Git Cheatsheet](http://www.git-tower.com/blog/git-cheat-sheet/)
* [Git Flow Cheatsheet](http://danielkummer.github.io/git-flow-cheatsheet/)

___

[BEEVA](http://www.beeva.com) | 2015
