# sensitive-data-scrubber

A Clojure library designed to scrub sensitive data such as social security numbers and credit card numbers from strings.

## Building the library

You'll need to have [Leiningen](http://leiningen.org/) installed. Then clone the repository and do:
    lein deps uberjar

## Usage

After building you can run it from a command prompt using
    java -jar SensitiveInfoFilter-0.1.0-standalone.jar "string to scrub"

I've also created a VERY rudimentary web interface for testing it out and included a `Procfile` for use in deploying to [Heroku](http://heroku.com).

## License

Copyright © 2012 David Kincaid

Distributed under the Eclipse Public License, the same as Clojure.
