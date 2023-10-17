README- project 1
Niharika Adari U78476085
****************************
HOW TO LOAD:
Unzip the folder and in the directory with the files, in the console, compile the java code using
	javac *.java
then, type
	java P1
The 'help' command will provide more information on all of the commands.

****************************
ADDITIONAL INFORMATION:
The program does not deal with duplicate entries as it was not required to handle in the project instructions.

load_coaches filename, load_teams filename
my code will print the number of rows that were successfully loaded. 
If a row is rejected, the row number is printed along with the validation error

teams_by_city CITY:
the program prints the team information in one row, and all of the relevant coach's names in the second row (first name, last name)

*************
commands:
add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN SEASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data
add_team ID LOCATION NAME LEAGUE - add a new team
print_coaches - print a listing of all coaches
print_teams - print a listing of all teams
coaches_by_name NAME - list info of coaches with the specified name
teams_by_city CITY - list the teams in the specified city
load_coaches FILENAME - bulk load of coach info from a file
load_team FILENAME - bulk load of team info from a file
best_coach SEASON - print the name of the coach with the most netwins in a specified season
search_coaches field=VALUE - print the name of the coach satisfying the specified conditions
	