import { Meteor } from 'meteor/meteor';
const oboe = require('oboe');
const fs = require('fs');

Meteor.startup(() => {
  // code to run on server at startup
  Products.remove({});
  // load the data into collections
  console.log('Startup server');
  getRecommendations();
  getMetadata();
});

function getRecommendations () {
	var os = oboe(fs.createReadStream(Assets.absoluteFilePath('data/adsorptionresults.json')));
	os.node('recommendations.*', Meteor.bindEnvironment(function (rec) {
		Products.insert(rec);
		return oboe.drop;
	}));
}

function getMetadata() {
	var os = oboe(fs.createReadStream(Assets.absoluteFilePath('data/metadata_results.json')));
	os.node('products.*', Meteor.bindEnvironment(function (p) {
        var product = Products.findOne({'id': p.asin});
		if (product) {
			Products.update(product._id, {$set : {
				'title' : 		p.title,
				'description' : p.description
			}});
		}
		return oboe.drop;
	}));
}
