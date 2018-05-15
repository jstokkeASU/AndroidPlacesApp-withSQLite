DROP TABLE place;
CREATE TABLE place (
id integer PRIMARY KEY NOT NULL UNIQUE ,
name text NOT NULL,
description text,
category text,
address_title text,
address_street text,
elevation text,
latitude text,
longitude text
);

INSERT INTO place VALUES(1,"ASU-West","Home of ASU's Applied Computing Program","School","ASU West Campus", "13591 N 47th Ave
Phoenix AZ 85051", "1100.0","33.608979","-112.159469");
INSERT INTO place VALUES(2,"UAK-Anchorage","University of Alaska's largest campus","School","University of Alaska at Anchorage","290 Spirit Dr
Anchorage AK 99508","0.0","61.189748","-149.826721");
INSERT INTO place VALUES(3,"Toreros","The University of San Diego, a private Catholic undergraduate university.","School","University of San Diego","5998 Alcala Park
San Diego CA 92110","0.0","32.771923","-117.188204");
INSERT INTO place VALUES(4,"Barrow-Alaska","The only real way in and out of Barrow Alaska.","Travel","Will Rogers Airport","1725 Ahkovak St
Barrow AK 99723","5.0","71.287881","-156.779417");
INSERT INTO place VALUES(5,"Calgary-Alberta","The home of the Calgary Stampede Celebration","Travel","Calgary International Airport","2000 Airport Rd NE
Calgary AB T2E 6Z8 Canada","3556.0","51.131377","-114.011246");
INSERT INTO place VALUES(6,"London-England","Renaissance Hotel at the Heathrow Airport","Travel","Renaissance London Heathrow Airport","5 Mondial Way
Harlington Hayes UB3 UK","82.0","51.481959","-0.445286");
INSERT INTO place VALUES(7,"Moscow-Russia","The Marriott Courtyard in downtown Moscow","Travel","Courtyard Moscow City Center","Voznesenskiy per 7 
 Moskva Russia 125009","512.0","55.758666","37.604058");
INSERT INTO place VALUES(8,"New-York-NY","New York City Hall at West end of Brooklyn Bridge","Travel","New York City Hall","1 Elk St
New York NY 10007","2.0","40.712991","-74.005948");
INSERT INTO place VALUES(9,"Notre-Dame-Paris","The 13th century cathedral with gargoyles, one of the first flying buttress, and home of the purported crown of thorns.","Travel","Cathedral Notre Dame de Paris","6 Parvis Notre-Dame Pl Jean-Paul-II
75004 Paris France","115.0","48.852972","2.349910");
INSERT INTO place VALUES(10,"Circlestone","Indian Ruins located on the second highest peak in the Superstition Wilderness of the Tonto National Forest. Leave Fireline at Turney Spring (33.487610,-111.132400)","Hike","","","6000.0","33.477524","-111.134345");
INSERT INTO place VALUES(11,"Reavis-Ranch","Historic Ranch in Superstition Mountains famous for Apple orchards","Hike","","","5000.0","33.491154","-111.155385");

