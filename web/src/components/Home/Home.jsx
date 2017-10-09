import React, {Component} from 'react';
import Dropzone from 'react-dropzone';

class Home extends React.Component {
    constructor() {
        super();

        this.state = {
            itenFile: "",
            destFile: "",
            test: false,
            svgFile: "",
        };
    }


    render()
    {

        return <div className="home-container">
            <div className="inner">

                <h1>T15 - Wolf Pack</h1>
                <h3>TripCo Itinerary</h3>

                <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Load your Itinerary here</button>
                </Dropzone>
                <Dropzone className="dropzone-style" onDrop={this.drop2.bind(this)}>
                    <button>Load extra information about destinations here</button>
                </Dropzone>
                <Dropzone className="dropzone-style" onDrop={this.drop3.bind(this)}>
                    <button>Click here to load your map</button>
                </Dropzone>
                <h3>{this.props.info}</h3>
                {this.props.checkBoxes}
                <table className="pair-table">
                    {this.props.pairs}
                    <tbody>
                    <tr>
                        <td colSpan="2">Total:</td>
                        <td>{this.props.totalDistance}</td>
                    </tr>
                    </tbody>
                </table>

                <img src={this.state.svgFile.preview} alt='load a map!'/>
            </div>
        </div>
    }



    drop(acceptedFiles)
    {

        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    this.setState({itenFile: JSON.parse(e.target.result)});
                    this.setState({gotItenFile: true});
                    this.props.browseFile(this.state.itenFile);
                    if(this.state.gotItenFile && this.state.gotDestFile){
                        this.props.getData(this.state.itenFile, this.state.destFile);
                    }
                };
            })(file).bind(this);

            fr.readAsText(file);
        });

    }
    drop2(acceptedFiles)
    {
        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    this.setState({destFile: JSON.parse(e.target.result)});
                    this.setState({gotDestFile: true});
                    this.props.browseFile2(this.state.destFile);
                    if(this.state.gotItenFile && this.state.gotDestFile){
                        this.props.getData(this.state.itenFile, this.state.destFile)
                    }
                };
            })(file).bind(this);

            fr.readAsText(file);
        });
    }
    drop3(acceptedFiles)
    {
        this.setState({svgFile:acceptedFiles[0]});
    }

}
export default Home