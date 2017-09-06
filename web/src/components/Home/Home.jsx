import React, {Component} from 'react';
import Dropzone from 'react-dropzone'

class Home extends React.Component {
    
    render() {
        
        return <div className="home-container">
            <div className="inner">
                <h1>T15 - Wolf Pack</h1>
                <h3>Itinerary</h3>
                <Dropzone className="dropzone-style" onDrop={this.drop.bind(this)}>
                    <button>Open JSON File</button>
                </Dropzone>
                <table className="pair-table">
                    {this.props.pairs}
                    <tbody>
                        <tr>
                            <td colSpan="2">Total:</td>
                            <td>{this.props.totalDistance}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    }
    
    drop(acceptedFiles) {
        console.log("Accepting drop");
        acceptedFiles.forEach(file => {
            console.log("Filename:", file.name, "File:", file);
            console.log(JSON.stringify(file));
            let fr = new FileReader();
            fr.onload = (function () {
                return function (e) {
                    let JsonObj = JSON.parse(e.target.result);
                    console.log(JsonObj);
                    this.props.browseFile(JsonObj);
                };
            })(file).bind(this);

            fr.readAsText(file);
        });
    }
}

export default Home
