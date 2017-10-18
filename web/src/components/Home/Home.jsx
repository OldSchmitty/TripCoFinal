import React, {Component} from 'react';
import Dropzone from 'react-dropzone';

class Home extends React.Component {
    constructor() {
        super();

        this.state = {
            test: false,
        };
    }


    render()
    {

        return <div className="home-container">
            <div className="inner">

                <h1>T15 - Wolf Pack</h1>
                <h3>TripCo Itinerary</h3>
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
            </div>
        </div>
    }
}
export default Home