# TODO
should money be a part of abstract Player class?
add money and then override getter/setters?

separate Game/BetManager into service/repo
combine BetManager and Game?

annotations for jackson

make Game extendable (make Game an abstract class, TexasHoldEm will be implementation)

### add Bet to repository
probably need to use table inheritance.
need to research superclass vs single table

need to decide who controls the morphing of user to player. 
should game hold a list of users in addition to list of players?