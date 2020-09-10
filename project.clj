(defproject com.taoensso/truss "1.6.0"
  :author "Peter Taoussanis <https://www.taoensso.com>"
  :description "Assertions API for Clojure/Script"
  :url "https://github.com/ptaoussanis/truss"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo
            :comments "Same as Clojure"}
  :min-lein-version "2.3.3"
  :global-vars {*warn-on-reflection* true
                *assert*             true
                ;; *unchecked-math*  :warn-on-boxed
                }

  :dependencies
  []

  :plugins
  [[lein-pprint    "1.3.2"]
   [lein-ancient   "0.6.15"]
   [lein-codox     "0.10.7"]
   [lein-cljsbuild "1.1.8"]]

  :profiles
  {;; :default [:base :system :user :provided :dev]
   :server-jvm {:jvm-opts ^:replace ["-server"]}
   :provided {:dependencies [[org.clojure/clojure       "1.7.0"]
                             [org.clojure/clojurescript "1.10.773"]]}
   :1.7      {:dependencies [[org.clojure/clojure       "1.7.0"]]}
   :1.8      {:dependencies [[org.clojure/clojure       "1.8.0"]]}
   :1.9      {:dependencies [[org.clojure/clojure       "1.9.0"]]}
   :1.10     {:dependencies [[org.clojure/clojure       "1.10.0"]]}
   :test     {:dependencies [[org.clojure/test.check    "1.1.0"]]}
   :depr     {:jvm-opts ["-Dtaoensso.elide-deprecated=true"]}
   :dev      [:1.10 :test :server-jvm :depr]}

  :cljsbuild
  {:test-commands
   {"node"    ["node" :node-runner "target/main.js"]
    "phantom" ["phantomjs" :runner "target/main.js"]}

   :builds
   [{:id :main
     :source-paths   ["src" "test"]
     ;; :notify-command ["terminal-notifier" "-title" "cljsbuild" "-message"]
     :compiler       {:output-to "target/main.js"
                      :optimizations :advanced
                      :pretty-print false}}]}

  :aliases
  {"start-dev"  ["with-profile" "+server-jvm" "repl" ":headless"]
   "build-once" ["cljsbuild" "once"]
   "deploy-lib" ["do" "build-once," "deploy" "clojars," "install"]
   "test-all"   ["do" "clean,"
                 "with-profile" "+1.10:+1.9:+1.8:+1.7" "test,"
                 "with-profile" "+test" "cljsbuild"    "test"]}

  :repositories
  {"sonatype-oss-public"
   "https://oss.sonatype.org/content/groups/public/"})
