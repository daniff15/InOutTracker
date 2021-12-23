# InOutTracker

Tracker for mall management capacity, that keeps track of entrances and exits of the stores in pandemic context.
It allows people to see if the mall is overloaded with other people so they can decide wheter to go or not.

## How it runs

Our service has a MySQL database and a pulsar standalone client running in the background. After this two components are running we start the SpringBoot API which will connect to the Database and connect a consumer to the pulsar client. After that, we start the data generation script that simulates real life flow for one mall (at the moment). In this python script we have to connect to the api and pulsar client. There's a producer for the message broker and in this script we populate the DB with a few data just for demonstration purposes. It periodically sends messages to the message broker (pulsar) through the created producer. The API consumer receives this messages and updates the Database with the new information that later will be accesses via mobile and web apps that get database info from the API.

## How to run

Simply run the script 'run.sh' with:

$ `./run-sh`

and wait until the browser opens the webapp

(You may need to give it permission to run `$ chmod +x run.sh`)

This script will:
- Build and run the service backend with the docker-compose.yml file 
- Run the data generation script to simulate real life data
- Open the webapp to check results

## Students identification 

98188 - Daniel Francisco - Architect

98324 - Henrique Sousa - Team manager(coordinator)

98498 - Daniel Figueiredo - DevOps master

100244 - Rui Delfim de Oliveira Júnior - Product owner

## Bookmarks

links to quickly access all project resources, such as project
management boards, editable versions of the reports in the cloud, entry point for
your API documentation,....


Mobile App Pages and User Stories Overview 
https://github.com/daniff15/InOutTracker/tree/dev/projmobile/mockup_overview
