# README
# Description
This project is a mission of Commander Lando Calrissian in the New Republic.
The target is to be able to handle YVH droids.

# Requirements
- *Java* version >= 17 (openJDK Temurin-17.0.3+7 recommended).
- *Gradle* version >= 7.4.x.

# Compile
Download the repository and once you are in it run the following command: `./gradlew clean build`

# Run it
To start the application just type the following `./gradle bootRun`

The application listens at port `8888` and the endpoint `/radar` 

## Get Next Target to attack
It returns the coordinates of the next target to attack based on a sequence
of scans.

### Request

`POST /radar/`

    curl --silent --location --request POST 'http://localhost:8888/radar' --header 'Content-Type: application/json' --data-raw '{"protocols":["avoid-mech"],"scan":[{"coordinates":{"x":0,"y":40},"enemies":{"type":"soldier","number":10}},{"coordinates":{"x":0,"y":80},"allies":5,"enemies":{"type":"mech","number":1}}]}' 

### Response

    {"x":0,"y":40}


# Scalability
The current approach it is based on memory resolution for these scans of available resources. 
Since the "store" (space complexity `O(n)` ) relies on memory, this is fast in response times, but the problem is when the
number of resources is huge. It could face a lack of memory.

The proposal could be to use
an external DB to face these problems. The response time would be impacted, but huge amount of
resources could be handled.

The time complexity is `O(n log(n))` in worst case scenario, since a sorted set is needed in order to provide 
the closest of furthest target.