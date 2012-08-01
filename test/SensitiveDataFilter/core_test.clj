(ns SensitiveDataFilter.core-test
  (:use clojure.test
        SensitiveDataFilter.core
        [midje sweet]))

(def ssn ["312-65-1234" "312651234" "312:65$1234" "312,65,1234"])
(def ssn-not ["666-22-3333" "910-24-9876" "000-12-1212" "312-00-1212" "312-77-0000" ])
(def itin ["910-77-1234" "976-99-9876"])
(def itin-not ["910-69-1234" "000-80-1234" "800-88-1234"])
(def creditcard ["378282246310005" "371449635398431" "378734493671000" "5610591081018250"
                 "30569309025904" "38520000023237" "6011111111111117" "6011000990139424"
                 "3530111333300000" "3566002020360505" "5555555555554444" "5105105105105100"
                 "4111111111111111" "4012888888881881" "4222222222222"])
(def creditcard-not ["1234567890123456" "111111111111111"])
(def test-value "SSN:535-35-6543 Visa: 4111111111111111 Age: 55")

(facts "About social security numbers"
  (map isSSN? ssn) => (has every? truthy)
  (map isSSN? ssn-not) => (has every? falsey))

(facts "About tax identification numbers"
  (map isITIN? itin) => (has every? truthy)
  (map isITIN? itin-not) => (has every? falsey))

(facts "About credit card numbers"
  (map isCCN? creditcard) => (has every? truthy)
  (map isCCN? creditcard-not) => (has every? falsey))

(facts "About removal of sensitive data"
  (remove-sensitive-data "SSN:535-35-6543") => "SSN:"
  (remove-sensitive-data test-value) => "SSN: Visa:  Age: 55")

