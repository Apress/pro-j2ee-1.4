DROP TABLE "MessageCMPTable"
;
CREATE TABLE "MessageCMPTable" ("description" VARCHAR(255) , "messageId" VARCHAR(255) , "type" VARCHAR(255), CONSTRAINT "pk_MessageCMPTabl" PRIMARY KEY ("messageId") )
;

DROP TABLE "CustomerCMPTable"
;
CREATE TABLE "CustomerCMPTable" ("_address_addressId" VARCHAR(255) , "_creditCard_creditCardId" VARCHAR(255) , "name" VARCHAR(255) , "password" VARCHAR(255) , "userId" VARCHAR(255), CONSTRAINT "pk_CustomerCMPTabl" PRIMARY KEY ("userId") )
;

DROP TABLE "BidCMPTable"
;
CREATE TABLE "BidCMPTable" ("bidId" VARCHAR(255) , "bidPrice" DECIMAL(15,5), CONSTRAINT "pk_BidCMPTabl" PRIMARY KEY ("bidId") )
;

DROP TABLE "CreditCardCMPTable"
;
CREATE TABLE "CreditCardCMPTable" ("_customer_userId" VARCHAR(255) , "creditCardId" VARCHAR(255) , "number" VARCHAR(255) , "type" VARCHAR(255), CONSTRAINT "pk_CreditCardCMPTabl" PRIMARY KEY ("creditCardId") )
;

DROP TABLE "OfferCMPTable"
;
CREATE TABLE "OfferCMPTable" ("askPrice" DECIMAL(15,5) , "description" VARCHAR(255) , "name" VARCHAR(255) , "offerId" VARCHAR(255) , "status" VARCHAR(255), CONSTRAINT "pk_OfferCMPTabl" PRIMARY KEY ("offerId") )
;

DROP TABLE "AddressCMPTable"
;
CREATE TABLE "AddressCMPTable" ("_customer_userId" VARCHAR(255) , "addressId" VARCHAR(255) , "city" VARCHAR(255) , "county" VARCHAR(255) , "postcode" VARCHAR(255) , "street" VARCHAR(255), CONSTRAINT "pk_AddressCMPTabl" PRIMARY KEY ("addressId") )
;

DROP TABLE "Offer_customer_Customer_offersTable"
;
CREATE TABLE "Offer_customer_Customer_offersTable" ("_Customer_userId" VARCHAR(255) , "_Offer_offerId" VARCHAR(255), CONSTRAINT "pk_Offer_customer_Customer_offersTabl" PRIMARY KEY ("_Offer_offerId") )
;

DROP TABLE "Message_customerByReceiverId_Customer_messagesByReceiverIdTable"
;
CREATE TABLE "Message_customerByReceiverId_Customer_messagesByReceiverIdTable" ("_Customer_userId" VARCHAR(255) , "_Message_messageId" VARCHAR(255), CONSTRAINT "pk_Message_customerByReceiverId_Customer_messagesByReceiverIdTabl" PRIMARY KEY ("_Message_messageId") )
;

DROP TABLE "Message_customerBySenderId_Customer_messagesBySenderIdTable"
;
CREATE TABLE "Message_customerBySenderId_Customer_messagesBySenderIdTable" ("_Customer_userId" VARCHAR(255) , "_Message_messageId" VARCHAR(255), CONSTRAINT "pk_Message_customerBySenderId_Customer_messagesBySenderIdTabl" PRIMARY KEY ("_Message_messageId") )
;

DROP TABLE "Message_offer_Offer_messagesTable"
;
CREATE TABLE "Message_offer_Offer_messagesTable" ("_Message_messageId" VARCHAR(255) , "_Offer_offerId" VARCHAR(255), CONSTRAINT "pk_Message_offer_Offer_messagesTabl" PRIMARY KEY ("_Message_messageId") )
;

DROP TABLE "Bid_offer_Offer_bidsTable"
;
CREATE TABLE "Bid_offer_Offer_bidsTable" ("_Bid_bidId" VARCHAR(255) , "_Offer_offerId" VARCHAR(255), CONSTRAINT "pk_Bid_offer_Offer_bidsTabl" PRIMARY KEY ("_Bid_bidId") )
;

DROP TABLE "Bid_customer_Customer_bidsTable"
;
CREATE TABLE "Bid_customer_Customer_bidsTable" ("_Bid_bidId" VARCHAR(255) , "_Customer_userId" VARCHAR(255), CONSTRAINT "pk_Bid_customer_Customer_bidsTabl" PRIMARY KEY ("_Bid_bidId") )
;