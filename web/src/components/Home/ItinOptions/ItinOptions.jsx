import React, {Component} from 'react';

class ItinOptions extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            options: props.options
        };}

    render() {
        let boxes = []
        for (let i in this.props.options) {
            boxes.push(
                <label className ="checky" key = {i}>
                    <input
                        type="checkbox"
                        value={i}
                        onChange={() => {
                            this.props.changeOpts(i);
                        }}
                    />
                    {i}
                </label>
            )
        }


        return (
            <div>{boxes}</div>
        )
    }


    // define additional methods here
}

export default ItinOptions;