(defproject SensitiveDataFilter "0.1.0"
  :description "Tool which attempts to strip sensitive information (credit card numbers, social security numbers, etc. from strings"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [noir "1.3.0-beta8"]
                 ]
  :profiles {:dev { :dependencies [[midje "1.4.0"]] }}
  :main SensitiveDataFilter.core)
