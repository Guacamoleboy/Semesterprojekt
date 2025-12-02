/* Used for testing .pdf and .svg -> .png generation */
UPDATE orders
SET status = 'offer'
WHERE status = 'accepted';

/* To check /beregn/modtag mail sending feature */
UPDATE customers
SET email = 'jonas69@live.dk'
WHERE email = 'jonas68@live.dk';