Trinkets
=======

Better than clothes.

Join us on Discord [here](https://discord.gg/RBkucXN)

## Making a Trinket

All items MUST have the `trinkets:trinket` tag. 
This will allow the item to be placed in any Trinket slot

If you wish to only allow the Trinket to be placed in a certain slot(s),
add the item to the type tags such as `trinkets:ring` or `trinkets:glove`

You do not need to implement `ITrinket` interface

## Making a Trinket Type

Calling `TrinketsApi.instance().createTrinketType()` will create and return a new Trinket Type.
It will also create a new tag using that Identifier.


## Making a Trinket Space (AKA Trinket Slot)

Calling `TrinketsApi.instance().createTrinketSpace()` will create and return a new Trinket Space.
This "space" is basically a slot in the Trinkets GUI.
There can be only 1 space in each grid position.
Currently, the Trinkets GUI is limited to 5 across and 4 down, for a total of 20 spaces.

