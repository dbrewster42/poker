A. add, subtract, and return money!!!

//1. decide winning hand at end of betting
//2. app should not allow deal unless betting has finished. 
//3. return money to front end
4. after bets complete, it auto-deals the next card. either the controller method needs to account for this
or it should require a manual call.
5. add river cards to each hand to avoid getRiverCards
6. add blinds
7. add a space between rounds in betMessages

## Break Ties
is there a way to break the tie no matter which or do I have to calculate
for each different hand? 
Would it be better if the score included the high card?

## clean up
change all println to log
add custom exceptions
get rid of Response

## bugs
Raise should only include extra amount. + raise needs to be clearer in the front end
How do we define who has bet already for the raise?
betting should start with bigBlind and then auto bet on first turn
should we set turnNumber on newRound or adjustTurn?