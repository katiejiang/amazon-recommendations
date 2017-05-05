import { Meteor } from 'meteor/meteor';
const oboe = require('oboe');
const fs = require('fs');

Meteor.startup(() => {
  // code to run on server at startup
  Products.remove({});

  // load the data into collections
  getRecommendations();
});

function getRecommendations () {
	var os = oboe(fs.createReadStream(Assets.absoluteFilePath('data/testresults.json')));
	os.node('recommendations.*', Meteor.bindEnvironment(function (rec) {
		console.log(rec);
		Products.insert(rec);
		return oboe.drop;
	}));
}
