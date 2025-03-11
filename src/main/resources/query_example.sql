/* check all orders with ingredients */
select *
from
    taco_order_tacos ot
        inner join taco_order o
                   on ot.taco_order_id = o.id
        inner join taco t
                   on ot.tacos_id = t.id
        inner join taco_ingredients i
                   on i.taco_id = t.id

/* check all users with roles */
SELECT *
FROM APP_USER au
         JOIN app_user_roles r
              ON au.id = r.app_user_id
         JOIN role rr
              ON r.roles_id = rr.id