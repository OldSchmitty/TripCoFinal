import React, {Component} from 'react';

class Pair extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data : []

        };}

    render() {
        this.state.data = Object.values(this.props).map((obj) => {
            return <td><h5>{obj}</h5></td>;
        });

        // Place the return statement from the stateless Pair here:
        return (
            <tbody className="pair">
            <tr>
                {this.state.data}
            </tr>
            </tbody>
        )
    }


    // define additional methods here
}

export default Pair;