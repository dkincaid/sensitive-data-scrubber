(defproject SensitiveDataFilter "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [ring/ring-jetty-adapter "1.0.1"]]
  :profiles {:dev { :dependencies [[midje "1.4.0"]] }}
  :main SensitiveDataFilter.core)
