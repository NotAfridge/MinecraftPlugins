# uAuction
Chest Auction System


```
/auction create auctionname     48   50   5   true
                ^               ^    ^    ^   ^
                | Auction Name  |    |    |   |
                                |    |    |   | Is the chest able to be viewed? Useful for a surprise auction.
                                |    |    | The minimum bidding requirement for each bid. Can change in config.yml
                                |    | The reserve/starting bid for the auction. Can change defaults in config.yml
                                | The time that it expires, in hours. Defaults to whatever is set in config.yml

/auction open auctionname     true
              ^               ^
              | Auction Name  |
                              | Will the auction broadcast a message saying that it's open?

/auction close auctionname
  Self explanatory.

/auction edit auctionname
  This will edit the auction, it must be closed first.

/auction give playername auctionname
  This will give the player the auction contents, regardless of status.

/auction delete auctionname
  This will delete a single auction, regardless of status.

/auction purge
  This will delete all auctions, regardless of status.

/auction maintenance
  This will temporarily stop all auctions from running.

/auction list
  This will list all open auctions, list is refreshed every hour, or on creation.
  You can also use /alist

/auction bid auctionname 50
  Places a bid on an auction, previous bids will be refunded. Can use doubles, 50.3 etc.
  You can also use /abid

/auction view auctionname
  Can view the contents of an auction, read only. This won't work for silent auctions.
  You can also use /aview

/auction info auctionname
  View the current status of the auction, the highest bid, who is winning etc.
  You can also use /ainfo

/auction collect auctionname
  If the player has won the auction, it will drop all it's contents naturally at the player.
  You can also use /acollect

/auction won
  Checks to see if you have won any auctions recently.
  You can also use /awon

/auction chat
  Activates reminders about current auctions in your chat.
  You can also use /achat

/auction
  This will display help, just like above, but in less detail.
```
