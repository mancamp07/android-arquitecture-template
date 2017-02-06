[![BuddyBuild](https://dashboard.buddybuild.com/api/statusImage?appID=5898c45c16ae6a01005f08ee&branch=master&build=latest)](https://dashboard.buddybuild.com/apps/5898c45c16ae6a01005f08ee/build/latest?branch=master)

# Pet Store app - Android architecture showcase
This is an Android project that serves as a showcase for an app following a Clean architecture approach. This is a result of an iteration over different approaches we've been using in our apps.

## View
The view are interfaces that could be implemented by any android view such as Activity, Fragment or Custom View. These views are dummy in the sense that they only act in response to orders from the presentation layer.
## Presentation
Serves as a middle man between Views (abstractions over Android specific components) and Business logic (Interactors).
## Interactors
Each interactor is a reusable component that executes a specific business logic such as "Add a pet", "Edit a pet", "Delete a pet". It fetches the data from a repository, executes business logic and returns the result to the presenter.
## Repository
Abstracts the datasources from which the Interactors get the data to act upon. This sample uses a database as a local datasource and cache mechanism and also a remote datasource that syncs with with the server using a REST api.
The repository first persist the changes locally and then syncs with the server. It uses the local data unless it's told that the cached data is stale, thus needing to sync with the server. The syncing mechanism is pretty simple: wipe the database and store the remote items again - this could be improved. 

**Note:** instead of using callbacks to communicate between layers we use Observables to provide the data upstream. Each of the inner layers can transform the data in a way the outer layer can understand it.

## Testing

### Unit testing
Presenters, Interactors and Repositories are unit tested by using JUnit and Mockito. 
They are all framework agnostic components by using plain Java objects and no references to Android specific code thus making unit tests really easy to achieve.

### UI testing
We use Espresso tests together with Mockito for integration and UI tests. Mockito allows us to mock the Observables returned from the repository to be able to test different states of the ui like success, failure and progress.

## DI
We use dagger 2 as dependency injection framework which allows us to have a good control of the dependency graph and inject mocked dependencies while testing.
We organize the dependencies in modules in a way they can be easily interchangeable with mock modules while testing. We follow this approach https://google.github.io/dagger/testing.html#organize-modules-for-testability.
