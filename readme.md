# TODO

separate Game/BetManager into service/repo
combine BetManager and Game?

improve JSON serialization. aka annotations for jackson

make Game extendable (make Game an abstract class, TexasHoldEm will be implementation)

### add Bet to repository
probably need to use table inheritance.
need to research superclass vs single table

need to decide who controls the morphing of user to player. 
should game hold a list of users in addition to list of players?