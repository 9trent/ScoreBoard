Task requirements:
The boards support the following operations:
1. Start a game. When a game starts, it should capture (being initial score 0-0)
a. Home team
b. Away Team
2. Finish a game. It will remove a match from the scoreboard.
3. Update score. Receiving the pair score; home team score and away team score updates a game score
4. Get a summary of games by total score. Those games with the same total score will be returned ordered by the most recently added to our system.

Assumptions:
1. homeTeam is considered to be first in a match pair, but if names are swapped while updating or finishing the game, they are recognized as such, and logic takes this into consideration.
2. National team names are checked against a collection with allowed values, as this would ensure that the naming convention is the same across World Cup teams. Furthermore, no validation against invalid values (empty string, null, values with numbers, etc.) is required.
3. There is no prevention from adjusting the score for both teams at the same time. Also, updating by 2 or more goals (up and down) at once is allowed as well.
4. Score update without any change is ignored.
5. Updating a score for a non-existing match will create a new match with an initial score of 0-0 regardless of what score was passed.
6. Finishing a non-existing match will show info that no such match was found and nothing was finished. As mentioned earlier, a match is finished if a playing pair is found, regardless of team name order.
7. All System.out.println... instructions were left intentionally for better clarity in the console output. Therefore, they are not taken into consideration in the test cases. Also, App class with main method contains some method invocations and commented code. This was left intentionally.
