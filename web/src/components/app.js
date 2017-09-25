import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            sysFile: [],
            totalDist: [],
            keys: [],
            svgFile: ""
        }
    };

    render() {

        let total = this.state.totalDist;
        let pairs = this.state.allPairs;
        let displayKeys = this.state.keys
        let ps = pairs.map((pp) => {
            return <Pair {...pp}/>;
        });
        return (
            <div className="app-container">
                <Home
                    browseFile={this.browseFile.bind(this)}
                    pairs={ps}
                    totalDistance={total}
                    keys={displayKeys}
                    svgFile={this.state.svgFile}
                />
            </div>
        )
    }

    async browseFile(file) {
        console.log("Got file:", file);
        //For loop that goes through all pairs,
        let pairs = [];
        let totalDist = 0;
        let keys = []

        for (let i = 0; i < Object.values(file).length; i++) {

            // Get List of Possible Items to Display
            if(i == 0){
                keys = Object.keys(file[0]);
                console.log('The display keys are: ' + keys);
                }

            let start = file[i].start; //get start from file i
            let end = file[i].end; //get end from file i
            let dist = file[i].distance;
            totalDist = totalDist + dist;

            let p = {
                start: start,
                end: end,
                dist: dist
            };

            /*let p = [];
            for (var key in file[i]){
               p.push(file[i][key])
               if(key.localCompare("distance") == 0)
                    totalDist += dist;
            }*/

            pairs.push(p); //add object to pairs array
            console.log("Pushing pair: ", p); //log to console
        }

        //Here we will update the state of app.
        // Anything component (i.e. pairs) referencing it will be re-rendered
        this.setState({
            allPairs: pairs,
            sysFile: file,
            totalDist: totalDist,
            keys: keys,
            svgFile: "./images/map.svg"
        });
    }
}