/* check all orders with ingredients */
select *
from
    ingredient_ref i
        inner join taco t
                   on i.taco = t.id
        inner join taco_order o
                   on t.taco_order = o.id