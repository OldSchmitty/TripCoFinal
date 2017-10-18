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
                <h3>{this.props.info}</h3>
                <div >
                    {this.props.checkBoxes}
                </div>
            </div>
            <div >
                <table className="pair-table">
                    {this.props.pairs}
                    <tbody>
                    {this.props.bottomRow}
                    </tbody>
                </table>
            </div>
        </div>
    }
}
export default Home