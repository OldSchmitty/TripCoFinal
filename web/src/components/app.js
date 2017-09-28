import React from 'react';
import Home from './Home/Home.jsx';
import Pair from './Home/Pair/Pair.jsx';

export default class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allPairs: [],
            sysFile: [],
            SysFile2: [],
            totalDist: [],
            keys: []
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
                    browseFile2={this.browseFile2.bind(this)}
                    getData={this.getData.bind(this)}
                    sysFile={this.state.sysFile}
                    sysFile2={this.state.sysFile2}
                    pairs={ps}
                    totalDistance={total}
                    keys={displayKeys}
                />

            </div>
        )
    }

    async browseFile(file) {
        console.log("Got file:", file);
        this.setState({
            sysFile: file
        })
    }

    async browseFile2(file) {
        console.log("Got file again:", file);
        this.setState({
            sysFile2: file
        })
    }


    getData(idFile, infoFile, fileName, test=false){
        let pairs = [];
        let totalDist = 0;
        console.log("output 1",idFile);
        console.log("output 2",infoFile);
        for (let i = 0; i < Object.values(idFile).length; i++) { // note that "file" from sprint 1 was renamed to "idFile" in this code
            let start;
            let end;
            let startArrayFound = false;
            let endArrayFound = false;
            let startIndex = idFile[i].start;
            let endIndex = idFile[i].end;
            let dist = idFile[i].distance;
            // loop through the second JSON file to find the information associated with idFile[i]:


            for (let j = 0; j < Object.values(infoFile).length; j++) {


                if (infoFile[j].id == startIndex) {
                    start = infoFile[j];
                    startArrayFound = true;
                }
                if (infoFile[j].id == endIndex) {
                    end = infoFile[j];
                    endArrayFound = true;
                }
                // stop looping if both start and end info has been found:
                if (startArrayFound && endArrayFound) {
                    break;
                }
            }

            totalDist += dist;

            if(test){
                console.log("Start Index: ", startIndex);
                console.log("End Index: ", endIndex);
                console.log("Start: ", start);
                console.log("End: ", end)
                console.log("Distance: ", dist)
                console.log("Cumulative Distance: ", totalDist)
            }


            // add the extra info of the second JSON to p:
            let p = {};
            for(var key in start) {
                p["start "+key] = "Start "+ key +": "+ start[key];
            }
            console.log("start is :", start);
            for(var key in end) {
                p["end "+key] = "End "+ key + ": " + end[key];
            }
            console.log("end is :", end);
            p["distance"] = "Distance: " + dist;
            p["cumulativeDistance"] = "Cumulative: " + totalDist;

            pairs.push(p); //add object to pairs array
        }


        this.setState({
            allPairs: pairs,
            sysFile: idFile,
            sysFile2: infoFile,
            totalDist: totalDist
        });

    }



    getData1(file){
        let pairs = [];
        let totalDist = 0;
        for (let i = 0; i < Object.values(file).length; i++) {

            let start = file[i].start; //get start from file i
            let end = file[i].end; //get end from file i
            let dist = file[i].distance;
            totalDist = totalDist + dist;

            let p = {
                start: start,
                end: end,
                dist: dist
            };

            pairs.push(p); //add object to pairs array
            console.log("Pushing pair: ", p); //log to console
        }

        //Here we will update the state of app.
        // Anything component (i.e. pairs) referencing it will be re-rendered
        this.setState({
            allPairs: pairs,
            sysFile: file,
            totalDist: totalDist
        });
    }
}