(defproject playground "0.1.0-SNAPSHOT"

  :description
  "A playground for Clojure quick projects"

  :url
  "https://github.com/denisidoro/clj-playground"

  :license
  {:name "Apache License, Version 2.0"}

  :plugins
  [[lein-cloverage "1.0.13"]
   ;[lein-shell "0.5.0"]
   [lein-auto "0.1.3"]
   ;[lein-changelog "0.3.2"]
   ;[lein-vanity "0.2.0"]
   [lein-cljfmt "0.5.7"]
   ;[lein-kibit "0.1.6"]
   ;[lein-ancient "0.6.15"]
   ;[lein-eftest "0.5.8"]
   [lein-nsorg "0.2.0"]]

  :dependencies
  [[org.clojure/clojure "1.10.0"]

   ; Toolbelts
   [denisidoro/quark "0.6.0"]

   ; DI
   ;[com.stuartsierra/component "0.3.2"]

   ; Pedestal
   ;[io.pedestal/pedestal.service "0.5.5"]
   ;[io.pedestal/pedestal.jetty "0.5.5"]
   ;[io.pedestal/pedestal.service-tools "0.5.5"]
   ;[http-kit "2.3.0"]
   ;[io.aviso/pretty "0.1.34"]
   ;[ch.qos.logback/logback-classic "1.1.8" :exclusions [org.slf4j/slf4j-api]]
   ;[org.slf4j/jul-to-slf4j "1.7.22"]
   ;[org.slf4j/jcl-over-slf4j "1.7.22"]
   ;[org.slf4j/log4j-over-slf4j "1.7.22"]

   ; Tests
   [nubank/selvage "1.0.0-BETA"]
   [nubank/matcher-combinators "0.2.4"]]

  :min-lein-version
  "2.0.0"

  :resource-paths
  ["config" "resources"]

  :profiles
  {:dev     {:aliases        {"run-dev" ["trampoline" "run" "-m" "playground.core/foo"]}
             :resource-paths ["test/resources"]
             :dependencies   [
                              ;[io.pedestal/pedestal.service-tools "0.5.3"]
                              ]}
   :kaocha  {:dependencies [[lambdaisland/kaocha "0.0-418"]]}
   :uberjar {:aot          [playground.core]
             :uberjar-name "playground.jar"}}

  :aliases
  {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]}

  :cljfmt
  {:indents {flow               [[:block 1]]
             facts              [[:block 1]]
             idempotent-start   [[:block 1]]
             idempotent-stop    [[:block 1]]
             fact               [[:block 1]]
             non-test-action    [[:inner 0]]
             as-customer        [[:block 1]]
             as-of              [[:block 1]]
             assoc-if           [[:block 1]]
             let-entities       [[:block 2]]
             provided           [[:inner 0]]
             tabular            [[:inner 0]]
             try-type           [[:block 0]]
             with-fn-validation [[:block 0]]
             embeds             [[:block 0]]
             with-responses     [[:block 0]]
             defint             [[:block 0]]
             with-scopes        [[:block 1]]
             some-with-open     [[:block 1]]
             let-flow           [[:block 1]]
             defhandler         [[:block 2]]}}

  :main
  ^{:skip-aot true} playground.core)
