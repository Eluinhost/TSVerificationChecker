TSVerificationChecker
=====================

Checks the teamspeak authentication website to see if Minecraft accounts are verified and/or online inside Teamspeak

Command
-------

`/tsverify <checkOnline> *`

OR 

`/tsverify <checkOnline> player1 player2 player3`

Alias: /tsverifycheck

### Parameters:

`checkOnline` - if 'true' will also check if the accounts are online, if 'false' will just check if they are verified. Checking for online will take longer

`*/player1 player2` - Either * for all online players or a list of players to check

### Permission

tsverify.command - default op

### Configuration:

```yaml
skip update check: false
```
    
`skip update check` - whether to skip checking for updates
