import { View, Text, Button } from 'react-native';

import { useNavigation } from '@react-navigation/native';

const Profile = () => {
  const navigation = useNavigation<any>();
  return (
    <View>
      <Text>Profile</Text>
      <Button title="Home" onPress={() => navigation.navigate('Home')} />
    </View>
  );
};

export default Profile;
