-- :name save-customer! :! :n
-- :doc creates a new message using the name, message, and timestamp keys
INSERT INTO customers
(name, state)
VALUES (:name, :state)


-- :name get-customers :? :*
-- :doc selects all available messages
SELECT * from customers
