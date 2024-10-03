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
