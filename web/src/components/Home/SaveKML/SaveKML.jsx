import React, {Component} from 'react';
import ButtonToolbar from "react-bootstrap/es/ButtonToolbar";
class SaveKML extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            KMLFile: this.props.KMLFile
        };
    }

    componentWillReceiveProps(nextProps) {
        if(this.state.KMLFile !== nextProps.KMLFile){
            this.setState({KMLFile: nextProps.KMLFile});
        }
    }

    render() {
        return (
            <div>
                <ButtonToolbar className="SaveKML">
                    <button type="button" onClick={this.saveButtonClicked.bind(this)}>
                        Save Trip as KML File
                    </button>
                </ButtonToolbar>
            </div>
        )
    }

    saveButtonClicked(event) {
        this.getFile();
    }

    async getFile() {
        console.log(this.state.KMLFile);
        if(this.state.KMLFile !== '') {
            let pom = document.createElement('a');
            pom.setAttribute('href', 'data:text/plain;charset=utf-8,'
                + encodeURIComponent(this.state.KMLFile));
            pom.setAttribute('download', "TripCoTrip.kml");

            if (document.createEvent) {
                let event = document.createEvent('MouseEvents');
                event.initEvent('click', true, true);
                pom.dispatchEvent(event);
            } else {
                pom.click();
            }

            if (pom.parentNode) {
                pom.parentNode.removeChild(pom);
            }
        }
    }

}

export default SaveKML;