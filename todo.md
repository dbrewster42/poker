
4. change response to GameResponse and then calculate winner automatically after game is over
6. add blinds
7. add a space between rounds in betMessages -- 
8. add logic for two winners in case of multi-tie

## Break Ties
is there a way to break the tie no matter which or do I have to calculate
for each different hand? 
Would it be better if the score included the high card?
Add another pojo to wrap PokerHand and hold Cards

## clean up
change all println to log
add custom exceptions

## bugs
raise needs to be clearer in the front end
betting should start with bigBlind and then auto bet on first turn

## player vs user
condense?
update player with each hand + money lost/won for each round
change player to GamePlayer? or move logic from player to PService?

### money
A. add, subtract, and return money!!!
B. should I display other's money?

### packaging 
service - game - player 