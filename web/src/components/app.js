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
            options: {},
            ps: [],
            addInfo: ""
        }
    };


    render() {

        let total = this.state.totalDist;
        let pairs = this.state.allPairs;
        let displayKeys = this.state.keys;
        this.state.ps = pairs.map((pp) => {
            let finalData={};
            finalData["start name"] = pp["start name"];
            finalData["end name"] = pp["end name"];
            finalData["distance"] = pp["distance"];
            finalData["cumulativeDistance"] = pp["cumulativeDistance"];

            for (let i in this.state.options){
                if(this.state.options[i]) {
                    finalData["start " + i] = pp["start " + i];
                    finalData["end " + i] = pp["end " + i];
                }
            }
            return <Pair {...finalData}/>;
        });
        let boxes = [];
        for (let i in this.state.options){
            boxes.push(<div className="checkbox">
                <label>
                    <input
                        type="checkbox"
                        value={i}
                        onChange={() => {
                            this.state.options[i]=!this.state.options[i];
                            console.log("options: ", this.state.options[i]);
                            this.forceUpdate()
                        }}
                    />

                    {i}
                </label>
            </div>)
        }

        return (
            <div className="app-container">
                <Home
                    browseFile={this.browseFile.bind(this)}
                    browseFile2={this.browseFile2.bind(this)}
                    getData={this.getData.bind(this)}
                    sysFile={this.state.sysFile}
                    sysFile2={this.state.sysFile2}
                    pairs={this.state.ps}
                    totalDistance={total}
                    keys={displayKeys}
                    checkBoxes={boxes}
                    info={this.state.addInfo}
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


    getData(idFile, infoFile) {
        let pairs = [];
        let totalDist = 0;
        for (let i = 0; i < Object.values(idFile).length; i++) {
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

            // add the extra info of the second JSON to p:
            let p = {};
            for (let key in start) {
                p["start " + key] = "Start " + key + ": " + start[key];
            }
            for (let key in end) {
                p["end " + key] = "End " + key + ": " + end[key];
            }
            p["distance"] = "Distance: " + dist;
            p["cumulativeDistance"] = "Cumulative: " + totalDist;

            pairs.push(p); //add object to pairs array
        }
        for(let k  in infoFile[0]){
            console.log("current k is: ", k);
            if(k != "name") {
                this.state.options[k] = false;
            }
        }




        this.setState({
            allPairs: pairs,
            sysFile: idFile,
            sysFile2: infoFile,
            totalDist: totalDist,
            addInfo: "Check boxes below to show/hide information about your trip!"
        });

    }
}