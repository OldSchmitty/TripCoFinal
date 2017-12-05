import React, {Component} from 'react';
import ButtonToolbar from "react-bootstrap/es/ButtonToolbar";
import Dropzone from 'react-dropzone';
class SaveLoad extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            sysFile: null,
            serverReturned: this.props.serverReturned
        };
    }

    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.serverReturned !== this.props.serverReturned) {
            this.setState({serverReturned: nextProps.serverReturned});
        }
    }

    render() {

        return (
            <div className= "saveload">
                <ButtonToolbar className= "Save-Load">
                    <Dropzone className="dropzone-style" onDrop={this.uploadButtonClicked.bind(this)}>
                        <button type="button" onClick={this.uploadButtonClicked.bind(this)}>
                            Upload Planned Trip</button>
                    </Dropzone>

                    <button type="button" onClick={this.saveButtonClicked.bind(this)}>
                        Save Trip</button>
                    <button type="button" onClick={this.resetPage.bind(this)}>Reset</button>
                </ButtonToolbar>
            </div>
        )
    }

    resetPage(){
        this.props.resetPage();
    }

    uploadButtonClicked(acceptedFiles) {
        if (!("dispatchConfig" in acceptedFiles)) {
            acceptedFiles.forEach(file => {
                let fr = new FileReader();
                fr.onload = (function () {
                    return function (e) {
                        let JsonObj = JSON.parse(e.target.result);
                        this.browseFile(JsonObj);
                    };
                })(file).bind(this);

                fr.readAsText(file);
            });
        }
    }

    saveButtonClicked(event) {
        this.getFile();
    }

    async getFile() {
        let locs = this.props.getTripTableData().map((location) => {
            return location.code;
        });

        let locationFile = {
            title: "selection",
            destinations: locs
        };

        let asJSONString = JSON.stringify(locationFile);
        let pom = document.createElement('a');
        pom.setAttribute('href', 'data:text/plain;charset=utf-8,'
            + encodeURIComponent(asJSONString));
        pom.setAttribute('download', "TripCoTrip.json");

        if (document.createEvent) {
            let event = document.createEvent('MouseEvents');
            event.initEvent('click', true, true);
            pom.dispatchEvent(event);
        } else {
            pom.click();
        }

       if(pom.parentNode) {
           pom.parentNode.removeChild(pom);
       }
    }

    async browseFile(file) {
        console.log("Got file:", file);
        this.setState({
            sysFile: file
        })
        this.fetch2("upload", this.state.sysFile.destinations);
    }

    /**
     * New server request method, One method to control the logic for request
     * @param type Type of request
     * @param input what to look for
     * @returns {Promise.<void>}
     */
    async fetch2(type, input) {
        let clientRequest;
        if (type === "query") {
            clientRequest = {
                queries: [input],
                doWhat: "query",
                units: this.props.units,
                opt: this.props.opt,
            };
        } else if (type === "upload") {
            clientRequest = {
                queries: input,
                doWhat: "upload",
                units: this.props.units,
                opt: this.props.opt,
            }
        } else {
            console.log("No valid type found")
        }
        await this.props.query(clientRequest);
        this.forceUpdate();
        if(type === "upload"){
            this.props.clearTrip();
            this.props.fillTripTable(this.state.serverReturned.items);
        }
    }
}

export default SaveLoad;