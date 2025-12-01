/* Used for testing .pdf and .svg -> .png generation */
UPDATE orders
SET status = 'offer'
WHERE status = 'accepted';