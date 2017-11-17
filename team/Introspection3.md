# Team *T15* - Inspection *3*
 
Inspection | Details
----- | -----
Subject | SearchSQLDatabase
Meeting | 11-17-17, Class
Checklist | Fox


### Roles
Name | Role | Preparation Time
--- | --- |--- 
Andrew | Developer | 
Josiah | Developer | 45 min
James  | Developer | 
Mark   | Developer | 

### Log
Line# | defect | h/m/l | github# | who agrees
-----| -------------------- |:---:|:---:| --------------------
2, 4, 6, 11, 13 | Unused imports                |  L  |     | Mark, Josiah       |
115  | For loop can be its own method |  H  |     | Mark          |
161  | Same as line 115               |  H  |     | Mark          |
197  | String can be final member     |  M  |     | Mark          |
257  | Same as 197                    |  M  |     | Mark          |
190, 256 | Both methods make a query, we should merge them | M | |Josiah
147, 94 | Both methods read a return from a query, we should merge them | H | | Josiah
83, 217 | "\*" does not show we are looking all data, rename to "all"? | L | | Josiah
36, 83, 46, 94, 148, 190, 256 | Prams for methods should be final | L | | Josiah
217 | If statement should be a switch statement | M | | Josiah
15 | Class not named in proper nameing convention | L | | Josiah
20, 24 | Wrong order of properties (static final private) -> (private static final) | L | | Josiah
197, 257 | Same query base string, Make into method of class var | L | | Josiah
161, 115 | same read query code make into its own method | H | | Josiah
97, 107, 150, 197, 256, 257 | Over 100 chars long | L | | Josiah
