import React, { Component } from 'react';

// This imports all of the external functionality we want from react-google-maps
import {GoogleMap, Marker, Polyline, withGoogleMap} from 'react-google-maps';


class Map extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentTrip : this.props.currentTrip,
            allPairs: this.props.allPairs
        };

        this.startLocation
    }

    /* Re-renders the map when currentTrip is updated*/
    componentWillReceiveProps(nextProps) {
        if (nextProps.currentTrip !== this.props.currentTrip) {
            this.setState({currentTrip: nextProps.currentTrip});
        }

        if (nextProps.allPairs !== this.props.allPairs) {
            this.setState({allPairs: nextProps.allPairs});
        }
    }

    /*
     * returns an array of coordinate pairs for google maps to draw the trip on the map
     */
    buildCoordinateArray() {

        var trip = [];

        // Add each coordinate pair to the trip
        if (this.state.allPairs.length > 0) {
            for (let i in this.state.allPairs) {
                trip.push(this.buildCoordinatePair(i));
            }
        } else this.startLocation = null;

        // make round trip again and update start location
        if (trip.length > 0) {
            trip.push(trip[0]);
            trip.push(trip[0]);
            this.startLocation = trip[0]
        }
            return trip
    }

    /*
     * Builds a pair of coordinates for a destination
     */
    buildCoordinatePair(i){
        let lng = this.getLongitude(i);
        let lat = this.getLatitude(i);
        return {lng: lng, lat: lat}
    }

    /*
     * returns Longitude of a destination
     */
    getLongitude(i){
        return parseFloat(this.state.allPairs[i]['start longitude'])
    }

    /*
     * returns Latitude of a destination
     */
    getLatitude(i){
        return parseFloat(this.state.allPairs[i]['start latitude'])
    }

    /*
     * returns the starting location coordinates
     */
    getStartLocation(){

        if (this.startLocation){
            return this.startLocation
        }
        return null
    }

    // Render method of the Map component
    render() {

        // Return the stuff we actually want rendered on the page
        return (
            { /*GoogleMap is an imported component from react-google-maps*/ },

            <GoogleMap
                defaultCenter={{lat: 0, lng: 0} /*Sets the default center for the map to start at */}
                defaultZoom={3 /* Sets the default zoom ie how much of the world is on the screen*/}
            >
            {/* Everything that is in between <GoogleMap> and </GoogleMap> get rendered onto the
                map. Polyline is an easy google library that draws lines from coordiates.*/ }
                <Polyline
                    visible={true /*Make sure the map is visable on screen*/}

                    path={this.buildCoordinateArray()/* Set polyline path to the coordiates array*/}

                    options={{
                        /* This is a list of optional things line line color and line weight this does not
                        need to be included. See documentation for more options*/
                        strokeColor: '#ff31a6',
                        strokeWeight: 4,
                    }}
                />
                <Marker
                    name = {"Start"}
                    position = {this.getStartLocation() /* Set start location */}
                />

            { /*Close our GoogleMap*/}
            </GoogleMap>
        )
    }
}
// This is important what this does is it wraps the Map module in
// a withGoogleMap module. Without this the map will not load
export default withGoogleMap(Map)
