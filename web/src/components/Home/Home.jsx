import React, {Component} from 'react';
import ItinOptions from "./ItinOptions/ItinOptions.jsx"
import Pair from './Pair/Pair.jsx';

class Home extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            options: this.props.options,
            ps: []
        };

        this.changeOpts = (i) => {
            this.state.options[i] = !this.state.options[i];
            this.forceUpdate();
        }

    }

    componentWillReceiveProps(nextProps) {
        // You don't have to do this check first, but it can help prevent an unneeded render
        if (nextProps.options !== this.props.options) {
            this.setState({options: nextProps.options});
        }
        this.forceUpdate();
    }


    hide(){
        if((Object.values(this.state.options).length > 0)) {
            return <h3>Check the boxes below for additional info!</h3>
        } else {
            return <h3></h3>
        }
    }

    makeTable(){
        this.state.ps = this.props.allPairs.map((pp) => {
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
    }


    render()
    {
        this.makeTable();
        return <div className="home-container">

            <div className="inner">
                {this.hide()}
                <ItinOptions options = {this.props.options} changeOpts = {this.changeOpts}/>
            </div>
            <div >
                <table className="pair-table">
                    {this.state.ps}
                    <tbody>
                    {this.props.bottomRow}
                    </tbody>
                </table>
            </div>
        </div>
    }
}
export default Home