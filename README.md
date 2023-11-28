# fieldwire_java_sample

Sample java code to integrate with Fieldwire:
  - [Fieldwire.java](app/src/net/fieldwire/Fieldwire.java) is the entry point
  - [Models](app/src/net/fieldwire/models) defines the models
  - [Endpoints](app/src/net/fieldwire/endpoints) defines the regional & super endpoints
  - [TokenManager](app/src/net/fieldwire/TokenManager.java) showcases programmatically refreshing the access token
  - [SampleCalls](app/src/net/fieldwire/SampleCalls.java) puts all the above together for particular use cases

### Setup
- Install the required java version (from `build.gradle`)
- Fill out the required pieces of info in [Fieldwire.java](app/src/net/fieldwire/Fieldwire.java) (Marked with `// REPLACE`)
- Install the dependencies & run using `./gradlew run`

### Note
Please store your tokens securely & use them across invocations of your integration setup to prevent hitting rate limits while refreshing the access token
