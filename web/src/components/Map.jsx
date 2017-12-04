import React, { Component } from 'react';

// This imports all of the external functionality we want from react-google-maps
import {GoogleMap, Polyline, withGoogleMap} from 'react-google-maps';


class Map extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentTrip : this.props.currentTrip,
        };
    }

    /* Re-renders the map when currentTrip is updated*/
    componentWillReceiveProps(nextProps) {
        if (nextProps.currentTrip !== this.props.currentTrip) {
            this.setState({currentTrip: nextProps.currentTrip});
        }
    }

    /* Path of lat and lng coordinates. Inorder for this to work path must be an array
           that contains objects that have a lat and lng property. This array can have other
           properties but it must at minimum have lat and lng.

           From my testing polyline will not draw a line if the path is invalid.
           Ie lat and lng must be valid cooridantes to work
    */
    buildCoordinateArray(){

        var trip = []

        if (this.state.currentTrip) {
            /* builds the trip and sorts using the itinerary */
            if ("itinerary" in this.state.currentTrip){
                trip = this.buildFromItinerary()
            }
            /* builds the trip without sorting by itinerary */
            else {
                trip = this.buildFromItems()
            }
        }

        return trip
    }

    /* Builds the coordinate array from the items, and sorts it using the itinerary*/
    buildFromItinerary(){

        var trip = this.buildFromItems()
        var sortedTrip = []

        /* remove last item - no longer round trip*/
        trip.pop()

        /* sort trip based on itinerary order */
        for (let i in this.state.currentTrip['itinerary']){
            var id = this.state.currentTrip['itinerary'][i]['sourceID']
            sortedTrip.push(trip[id])
        }

        /* Make round trip again */
        sortedTrip.push(sortedTrip[0])

        return sortedTrip
    }

    /* build from currentTrip.items - used when optimization has not been selected */
    buildFromItems(){
        var trip = []

        /* For earh item, add its coordinates to the array */
        for (let i in this.state.currentTrip.items) {
            var lat = this.state.currentTrip.items[i].map.latitude
            var lng = this.state.currentTrip.items[i].map.longitude
            trip.push({lat: parseFloat(lat), lng: parseFloat(lng)})
        }

        /* Make round trip - add coordinates of start destination to end of array to return home */
        trip.push({lat: parseFloat(this.state.currentTrip.items[0].map.latitude),
            lng: parseFloat(this.state.currentTrip.items[0].map.longitude)})

        return trip
    }

    // Render method of the Map component
    render() {

        // Return the stuff we actually want rendered on the page
        return (
            { /*GoogleMap is an imported component from react-google-maps*/ },
            <GoogleMap
                defaultCenter={{lat: 0, lng: 0} /*Sets the default center for the map to start at */}
                defaultZoom={1 /* Sets the default zoom ie how much of the world is on the screen*/}
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

            { /*Close our GoogleMap*/}
            </GoogleMap>
        )
    }
}
// This is important what this does is it wraps the Map module in
// a withGoogleMap module. Without this the map will not load
export default withGoogleMap(Map)
