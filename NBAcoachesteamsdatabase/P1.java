import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class P1 {

	/* Define data structures for holding the data here */
	private ArrayList<Coach> coaches;
	private ArrayList<Team> teams;

	public P1() {
		/* initialize the data structures */
		coaches = new ArrayList<Coach>();
		teams = new ArrayList<Team>();
	}

	public void run() {
		CommandParser parser = new CommandParser();

		System.out.println("The mini-DB of NBA coaches and teams");
		System.out.println("Please enter a command.  Enter 'help' for a list of commands.");
		System.out.println();
		System.out.print("> ");

		Command cmd = null;
		while ((cmd = parser.fetchCommand()) != null) {
			// System.out.println(cmd);

			boolean result = false;

			if (cmd.getCommand().equals("help")) {
				result = doHelp();

				/* You need to implement all the following commands */
			} else if (cmd.getCommand().equals("add_coach")) {

				String[] parameters = cmd.getParameters();
				result = addCoach(parameters);

			} else if (cmd.getCommand().equals("add_team")) {

				String[] parameters = cmd.getParameters();
				result = addTeam(parameters);

			} else if (cmd.getCommand().equals("print_coaches")) {

				result = printCoaches();

			} else if (cmd.getCommand().equals("print_teams")) {

				result = printTeams();

			} else if (cmd.getCommand().equals("coaches_by_name")) {

				String[] parameters = cmd.getParameters();
				result = coachesByName(parameters);

			} else if (cmd.getCommand().equals("teams_by_city")) {

				String[] parameters = cmd.getParameters();
				result = teamsByCity(parameters);

			} else if (cmd.getCommand().equals("load_coaches")) {

				// Read Filename parameter
				String[] parameters = cmd.getParameters();
				if (parameters.length > 0) {
					String filename = parameters[0];
					result = loadCoaches(filename);
				} else {
					System.out.println("Please pass the file name");
				}

			} else if (cmd.getCommand().equals("load_teams")) {
				// Read Filename parameter
				String[] parameters = cmd.getParameters();
				if (parameters.length > 0) {
					String filename = parameters[0];
					result = loadTeams(filename);
				} else {
					System.out.println("Please pass the file name");
				}
			} else if (cmd.getCommand().equals("best_coach")) {

				String[] parameters = cmd.getParameters();
				result = bestCoach(parameters);

			} else if (cmd.getCommand().equals("search_coaches")) {

				String[] parameters = cmd.getParameters();
				result = searchCoaches(parameters);

			} else if (cmd.getCommand().equals("exit")) {
				System.out.println("Leaving the database, goodbye!");
				break;
			} else if (cmd.getCommand().equals("")) {

			} else {
				System.out.println("Invalid Command, try again!");
			}

			if (result) {
				// ...
			}

			System.out.print("> ");
		}
	}

	private boolean doHelp() {
		System.out.println(
				"add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN SEASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data");
		System.out.println("add_team ID LOCATION NAME LEAGUE - add a new team");
		System.out.println("print_coaches - print a listing of all coaches");
		System.out.println("print_teams - print a listing of all teams");
		System.out.println("coaches_by_name NAME - list info of coaches with the specified name");
		System.out.println("teams_by_city CITY - list the teams in the specified city");
		System.out.println("load_coaches FILENAME - bulk load of coach info from a file");
		System.out.println("load_team FILENAME - bulk load of team info from a file");
		System.out
				.println("best_coach SEASON - print the name of the coach with the most netwins in a specified season");
		System.out.println(
				"search_coaches field=VALUE - print the name of the coach satisfying the specified conditions");
		System.out.println("exit - quit the program");
		return true;
	}

	private boolean addCoach(String[] parameters) {
		int ln = -1;
		// System.out.println(parameters.length);
		if (parameters.length == 9) {
			String coachid = parameters[0];
			String year = parameters[1];
			String firstname = parameters[2];
			String lastname = parameters[3];
			String seasonwin = parameters[4];
			String seasonloss = parameters[5];
			String playoffwin = parameters[6];
			String playoffloss = parameters[7];
			String team = parameters[8];

			firstname = firstname.replaceAll("\\+", " ");
			lastname = lastname.replaceAll("\\+", " ");

			if (validCoachValues(ln, coachid, year, firstname, lastname, seasonwin, seasonloss, playoffwin, playoffloss,
					team)) {
				// ignoring the 3rd column yr_order value to load into Coach object.
				Coach coach = new Coach(coachid.trim(), Integer.parseInt(year.trim()), firstname.trim(),
						lastname.trim(), Integer.parseInt(seasonwin.trim()), Integer.parseInt(seasonloss.trim()),
						Integer.parseInt(playoffwin.trim()), Integer.parseInt(playoffloss.trim()), team.trim());
				coaches.add(coach);
				System.out.println("Added successfully");
			}
		} else {
			System.out.println("Error - number of expected arguments should be 9");
		}

		return true;

	}

	private boolean addTeam(String[] parameters) {
		int ln = -1;
		// System.out.println(parameters.length);
		if (parameters.length == 4) {
			String teamid = parameters[0];
			String location = parameters[1];
			String name = parameters[2];
			String league = parameters[3];

			location = location.replaceAll("\\+", " ");
			name = name.replaceAll("\\+", " ");

			if (validTeamValues(ln, teamid, location, name, league)) {
				Team team = new Team(teamid.trim(), location.trim(), name.trim(), league.trim());
				teams.add(team);
				System.out.println("Added successfully");
			}
		} else {
			System.out.println("Error - number of expected arguments should be 4");
		}

		return true;

	}

	private boolean loadCoaches(String filename) {

		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String line;
			String[] coachvalues;
			int ln = 0;
			int sln = 0;

			while ((line = in.readLine()) != null) {
				if (ln == 0) {
					// System.out.println("Skipped header line");
				} else {

					coachvalues = line.split(",", -1);

					if (coachvalues.length == 10) {
						String coachid = coachvalues[0];
						String year = coachvalues[1];
						String yrorder = coachvalues[2];
						String firstname = coachvalues[3];
						String lastname = coachvalues[4];
						String seasonwin = coachvalues[5];
						String seasonloss = coachvalues[6];
						String playoffwin = coachvalues[7];
						String playoffloss = coachvalues[8];
						String team = coachvalues[9];
						if (validCoachValues(ln, coachid, year, firstname, lastname, seasonwin, seasonloss, playoffwin,
								playoffloss, team)) {
							// ignoring the 3rd column yr_order value to load into Coach object.
							Coach coach = new Coach(coachid.trim(), Integer.parseInt(year.trim()), firstname.trim(),
									lastname.trim(), Integer.parseInt(seasonwin.trim()),
									Integer.parseInt(seasonloss.trim()), Integer.parseInt(playoffwin.trim()),
									Integer.parseInt(playoffloss.trim()), team.trim());
							coaches.add(coach);
							// System.out.println("Loaded the line number " + (ln+1) + " - " +
							// coach.toString());
							sln++;
						}
					} else {
						System.out.println(
								"Skipped the line number " + ln + " - number of expected columns should be 10");
					}
				}

				ln++;
			}
			System.out.println("Loaded successfully " + sln + " rows");
			return true;
		} catch (IOException e) {
			// System.out.println("Error reading the File Name/File Path : " +
			// e.toString());
			System.out.println("The system cannot find the file specified " + filename);
			return false;
		}
	}

	private boolean validCoachValues(int ln, String coachid, String year, String firstname, String lastname,
			String seasonwin, String seasonloss, String playoffwin, String playoffloss, String team) {

		String errmsg = "";
		if (ln == -1) {
			errmsg = "Error";
		} else {
			ln = ln + 1;
			errmsg = "Rejected the line number " + ln;
		}

		if (coachid.trim().length() == 0 || coachid.trim().length() > 9) {
			System.out.println(errmsg + " - invalid coach_ID " + coachid + " , total length should be 9");
			return false;
		} else if (!coachid.trim().substring(0, coachid.trim().length() - 2).matches("[A-Z]*")) {
			System.out.println(
					errmsg + " - invalid coach_ID " + coachid + ", does not consist of less than 7 capital letters");
			return false;
		} else if (!coachid.trim().substring(coachid.trim().length() - 2).matches("^[0-9]{2}$")) {
			System.out.println(errmsg + " - invalid coach_ID " + coachid
					+ ", does not consist of less than 7 capital letters and two digits");
			return false;
		} else if (year.trim().length() > 0 && !year.trim().matches("^[0-9]{4}$")) {
			System.out.println(errmsg + " - invalid 4 digit year " + year);
			return false;
		} else if (seasonwin.trim().length() > 0 && !seasonwin.trim().matches("[0-9][0-9]*")) {
			System.out.println(errmsg + " - invalid season_win " + seasonwin + " - should be non-negative integer");
			return false;
		} else if (seasonloss.trim().length() > 0 && !seasonloss.trim().matches("[0-9][0-9]*")) {
			System.out.println(errmsg + " - invalid season_loss " + seasonloss + " - should be non-negative integer");
			return false;
		} else if (playoffwin.trim().length() > 0 && !playoffwin.trim().matches("[0-9][0-9]*")) {
			System.out.println(errmsg + " - invalid playoff_win " + playoffwin + " - should be non-negative integer");
			return false;
		} else if (playoffloss.trim().length() > 0 && !playoffloss.trim().matches("[0-9][0-9]*")) {
			System.out.println(errmsg + " - invalid playoff_loss " + playoffloss + " - should be non-negative integer");
			return false;
		} else if (team.trim().length() == 0 || !team.trim().matches("[\\p{Digit}\\p{Lu}]+")) {
			System.out.println(
					errmsg + " - invalid team " + team + " - does not consist of capital letters and/or digits");
			return false;
		}

		return true;
	}

	private boolean printCoaches() {
		if (coaches != null && coaches.size() > 0) {
			for (int i = 0; i < coaches.size(); i++) {
				System.out.println(coaches.get(i).toString());
			}
			return true;
		} else {
			System.out.println("Coaches data is not available");
			return true;
		}
	}

	private boolean loadTeams(String filename) {

		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {
			String line;
			String[] teamvalues;
			int ln = 0;
			int sln = 0;

			while ((line = in.readLine()) != null) {
				if (ln == 0) {
					// System.out.println("Skipped header line");
				} else {

					teamvalues = line.split(",", -1);

					if (teamvalues.length == 4) {
						String teamid = teamvalues[0];
						String location = teamvalues[1];
						String name = teamvalues[2];
						String league = teamvalues[3];

						if (validTeamValues(ln, teamid, location, name, league)) {
							Team team = new Team(teamid.trim(), location.trim(), name.trim(), league.trim());
							teams.add(team);
							// System.out.println("Loaded the line number " + (ln+1) + " - " +
							// team.toString());
							sln++;
						}
					} else {
						System.out
								.println("Skipped the line number " + ln + " - number of expected columns should be 4");
					}
				}

				ln++;
			}
			System.out.println("Loaded successfully " + sln + " rows");
			return true;
		} catch (IOException e) {
			// System.out.println("Error reading the File Name/File Path : " +
			// e.toString());
			System.out.println("The system cannot find the file specified " + filename);
			return false;
		}
	}

	private boolean validTeamValues(int ln, String teamid, String location, String name, String league) {

		String errmsg = "";
		if (ln == -1) {
			errmsg = "Error";
		} else {
			ln = ln + 1;
			errmsg = "Rejected the line number " + ln;
		}

		if (teamid.trim().length() == 0 || !teamid.trim().matches("[\\p{Digit}\\p{Lu}]+")) {
			System.out
					.println(errmsg + " - invalid team " + teamid
							+ " - does not consist of capital letters and/or digits");
			return false;
		} else if (location.trim().length() > 0 && location.trim().split("\\s+").length > 2) {
			System.out.println(
					errmsg + " - invalid Location " + location + " - does not consist of one or two English word(s)");
			return false;
		} else if (league.trim().length() == 0 || !league.trim().matches("^[A-Z]{1}$")) {
			System.out.println(errmsg + " - invalid League " + league + " - does not consist of one capital letter");
			return false;
		}

		return true;
	}

	private boolean printTeams() {
		if (teams != null && teams.size() > 0) {
			for (int i = 0; i < teams.size(); i++) {
				System.out.println(teams.get(i).toString());
			}
			return true;
		} else {
			System.out.println("Teams data is not available");
			return true;
		}
	}

	private boolean coachesByName(String[] parameters) {

		if (parameters.length > 0) {
			String lastname = parameters[0];

			lastname = lastname.replaceAll("\\+", " ");
			int sc = 0;
			String team = "";
			String teaminfo = "";
			for (int i = 0; i < coaches.size(); i++) {
				if (coaches.get(i).getLast_name().trim().equalsIgnoreCase(lastname)) {

					team = coaches.get(i).getTeam();
					for (int it = 0; it < teams.size(); it++) {
						Team t = teams.get(it);
						teaminfo = "";
						if (t.getTeam_ID().equals(team.trim())) {
							// get team information
							teaminfo = teams.get(it).getLocation() + " " + teams.get(it).getName();
							break;
						}
					}

					if (teaminfo.equals("")) {
						System.out.println(coaches.get(i).toString());
						sc++;
					} else {
						System.out.println(coaches.get(i).toString() + " " + teaminfo);
						sc++;
					}

				}
			}
			if (sc == 0) {
				System.out.println("No coaches found");
			}
			return true;
		} else {
			System.out.println("Please pass coach last_name");
			return false;
		}

	}

	private boolean teamsByCity(String[] parameters) {

		if (parameters.length > 0) {

			String location = parameters[0];

			location = location.replaceAll("\\+", " ");
			String names = "";
			int tc = 0;

			for (int i = 0; i < teams.size(); i++) {
				if (teams.get(i).getLocation().trim().equalsIgnoreCase(location)) {
					String teamID = teams.get(i).getTeam_ID();
					names = "";
					// get names of who coached teams in that city
					for (int ic = 0; ic < coaches.size(); ic++) {
						if (coaches.get(ic).getTeam().trim().equalsIgnoreCase(teamID)) {
							String coachname = coaches.get(ic).getFirst_name() + " " + coaches.get(ic).getLast_name();
							if (names.indexOf(coachname) < 0) {
								if (names.equals("")) {
									names = coachname;
								} else
									names = names + ", " + coachname;
							}
						}
					}

					System.out.println(teams.get(i).toString());
					if (!names.equals("")) {
						System.out.println(" " + names);
					} else
						System.out.println("No coaches found");
					tc++;
				}
			}

			if (tc == 0) {
				System.out.println("No teams found");
			}
			return true;
		} else {
			System.out.println("Please pass city name");
			return false;
		}
	}

	private boolean bestCoach(String[] parameters) {

		if (parameters.length > 0) {
			String season = parameters[0];

			int year = 0;
			try {
				year = Integer.parseInt(season);
			} catch (Exception e) {
				System.out.println("Invalid argument passed");
				return false;
			}

			int mostCoachNetWins = 0;
			int netWins;
			Coach c;
			String coachname = "";

			// get most Coach NetWins
			for (int i = 0; i < coaches.size(); i++) {
				c = coaches.get(i);

				if (c.getYear() == year) {
					netWins = (c.getSeason_win() - c.getSeason_loss()) + (c.getPlayoff_win() - c.getPlayoff_loss());

					if (netWins > mostCoachNetWins) {
						mostCoachNetWins = netWins;
						coachname = c.getFirst_name() + " " + c.getLast_name();
					}
				}
			}

			System.out.println(coachname);

			return true;
		} else {
			System.out.println("Please pass season year");
			return false;
		}
	}

	private boolean searchCoaches(String[] parameters) {

		int pcnt = parameters.length;

		if (pcnt > 0) {

			for (int ic = 0; ic < coaches.size(); ic++) {
				Coach c = coaches.get(ic);

				String searchcoachidvalue = "";
				String searchcoachseasonvalue = "";
				String searchcoachfirstnamevalue = "";
				String searchcoachlastnamevlaue = "";
				String searchcoachseasonwinvalue = "";
				String searchcoachseasonlossvalue = "";
				String searchcoachplayoffwinvalue = "";
				String searchcoachplayofflossvalue = "";
				String searchcoachteamvalue = "";

				String finalSearchCriteria = "";

				for (int i = 0; i < pcnt; i++) {
					String[] tokens = parameters[i].split("=");
					String field = tokens[0];
					String value = tokens[1];
					if (field != null && field.trim().equalsIgnoreCase("coach_id")) {
						searchcoachidvalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("season")) {
						searchcoachseasonvalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("first_name")) {
						searchcoachfirstnamevalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("last_name")) {
						searchcoachlastnamevlaue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("season_win")) {
						searchcoachseasonwinvalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("season_loss")) {
						searchcoachseasonlossvalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("playoff_win")) {
						searchcoachplayoffwinvalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("playoff_loss")) {
						searchcoachplayofflossvalue = value.trim();
					} else if (field != null && field.trim().equalsIgnoreCase("team")) {
						searchcoachteamvalue = value.trim();
					}
				}

				String coachid = c.getCoach_ID();
				String season = c.getYear() + "";
				String firstname = c.getFirst_name();
				String lastname = c.getLast_name();
				String seasonwin = c.getSeason_win() + "";
				String seasonloss = c.getSeason_loss() + "";
				String playoffwin = c.getPlayoff_win() + "";
				String playoffloss = c.getPlayoff_loss() + "";
				String team = c.getTeam();

				if (((searchcoachidvalue.equals(""))
						|| (!searchcoachidvalue.equals("") && coachid.equalsIgnoreCase(searchcoachidvalue))) &&
						((searchcoachseasonvalue.equals(""))
								|| (!searchcoachseasonvalue.equals("") && season.equals(searchcoachseasonvalue)))
						&&
						((searchcoachfirstnamevalue.equals("")) || (!searchcoachfirstnamevalue.equals("")
								&& firstname.equalsIgnoreCase(searchcoachfirstnamevalue)))
						&&
						((searchcoachlastnamevlaue.equals(""))
								|| (!searchcoachlastnamevlaue.equals("")
										&& lastname.equalsIgnoreCase(searchcoachlastnamevlaue)))
						&&
						((searchcoachseasonwinvalue.equals("")) || (!searchcoachseasonwinvalue.equals("")
								&& seasonwin.equals(searchcoachseasonwinvalue)))
						&&
						((searchcoachseasonlossvalue.equals("")) || (!searchcoachseasonlossvalue.equals("")
								&& seasonloss.equals(searchcoachseasonlossvalue)))
						&&
						((searchcoachplayoffwinvalue.equals("")) || (!searchcoachplayoffwinvalue.equals("")
								&& playoffwin.equals(searchcoachplayoffwinvalue)))
						&&
						((searchcoachplayofflossvalue.equals("")) || (!searchcoachplayofflossvalue.equals("")
								&& playoffloss.equals(searchcoachplayofflossvalue)))
						&&
						((searchcoachteamvalue.equals(""))
								|| (!searchcoachteamvalue.equals("")
										&& team.equalsIgnoreCase(searchcoachteamvalue)))) {

					System.out.println(c.toString());
				}

			}
			return true;
		} else {
			System.out.println("please pass at least one search criteria with format field=VALUE");
			return false;
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new P1().run();
	}

	// Create Coach Object
	private class Coach {
		private String coach_ID;
		private int year;
		private String first_name;
		private String last_name;
		private int season_win;
		private int season_loss;
		private int playoff_win;
		private int playoff_loss;
		private String team;

		public Coach(String coach_ID, int year, String first_name, String last_name, int season_win, int season_loss,
				int playoff_win, int playoff_loss, String team) {
			this.coach_ID = coach_ID;
			this.year = year;
			this.first_name = first_name;
			this.last_name = last_name;
			this.season_win = season_win;
			this.season_loss = season_loss;
			this.playoff_win = playoff_win;
			this.playoff_loss = playoff_loss;
			this.team = team;
		}

		public String getCoach_ID() {
			return coach_ID;
		}

		public void setCoach_ID(String coach_ID) {
			this.coach_ID = coach_ID;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public String getFirst_name() {
			return first_name;
		}

		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}

		public String getLast_name() {
			return last_name;
		}

		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}

		public int getSeason_win() {
			return season_win;
		}

		public void setSeason_win(int season_win) {
			this.season_win = season_win;
		}

		public int getSeason_loss() {
			return season_loss;
		}

		public void setSeason_loss(int season_loss) {
			this.season_loss = season_loss;
		}

		public int getPlayoff_win() {
			return playoff_win;
		}

		public void setPlayoff_win(int playoff_win) {
			this.playoff_win = playoff_win;
		}

		public int getPlayoff_loss() {
			return playoff_loss;
		}

		public void setPlayoff_loss(int playoff_loss) {
			this.playoff_loss = playoff_loss;
		}

		public String getTeam() {
			return team;
		}

		public void setTeam(String team) {
			this.team = team;
		}

		@Override
		public String toString() {
			return coach_ID + " " + year + " " + first_name + " " + last_name + " " + season_win + " " + season_loss
					+ " " + playoff_win + " " + playoff_loss + " " + team;
		}

	}

	private class Team {
		private String team_ID;
		private String location;
		private String name;
		private String league;

		public Team(String team_ID, String location, String name, String league) {
			this.team_ID = team_ID;
			this.location = location;
			this.name = name;
			this.league = league;
		}

		public String getTeam_ID() {
			return team_ID;
		}

		public void setTeam_ID(String team_ID) {
			this.team_ID = team_ID;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLeague() {
			return league;
		}

		public void setLeague(String league) {
			this.league = league;
		}

		@Override
		public String toString() {
			return team_ID + " " + location + " " + name + " " + league;
		}

	}
}
